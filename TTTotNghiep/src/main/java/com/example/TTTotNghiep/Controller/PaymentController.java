package com.example.TTTotNghiep.Controller;


import com.example.TTTotNghiep.Config.ConfigPayment;
import com.example.TTTotNghiep.dto.PaymentResDTO;
import com.example.TTTotNghiep.dto.TransactionStatusDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/")
public class PaymentController {
    @GetMapping("api/payment/create-new")
    public ResponseEntity<?> createPayment(@RequestHeader("Authorization") String jwt, @RequestParam long amountin) throws UnsupportedEncodingException {
//        long amount = 10000000;


        long amount = amountin*100*25135;
        String bankCode = "NCB";//req.getParameter("bankCode");

        String vnp_TxnRef = ConfigPayment.getRandomNumber(8);
//        String vnp_IpAddr = ConfigPayment.getIpAddress(req);

        String vnp_TmnCode = ConfigPayment.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", ConfigPayment.vnp_Version);
        vnp_Params.put("vnp_Command", ConfigPayment.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_ReturnUrl", ConfigPayment.vnp_ReturnUrl);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", ConfigPayment.orderType);
        vnp_Params.put("vnp_Locale", "vn");

//        String locate = req.getParameter("language");
//        if (locate != null && !locate.isEmpty()) {
//            vnp_Params.put("vnp_Locale", locate);
//        } else {
//            vnp_Params.put("vnp_Locale", "vn");
//        }
        vnp_Params.put("vnp_ReturnUrl", ConfigPayment.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr",  "123.123.123.123");
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigPayment.hmacSHA512(ConfigPayment.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigPayment.vnp_PayUrl + "?" + queryUrl;


        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus("Ok");
        paymentResDTO.setMessage("Successfully");
        paymentResDTO.setURL(paymentUrl);
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));

        return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
    }


    @GetMapping("/payment-infor")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vpn_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String order,
            @RequestParam(value = "vpn_ResponseCode") String responseCode
    ) throws UnsupportedEncodingException {
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        if(responseCode.equals("00")){
            transactionStatusDTO.setMessage("Successfully");
            transactionStatusDTO.setStatus("Ok");
            transactionStatusDTO.setData("");
        }else{
            transactionStatusDTO.setData("");
            transactionStatusDTO.setMessage("Failed");
            transactionStatusDTO.setStatus("No");
        }


        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);
    }
}

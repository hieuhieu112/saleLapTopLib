package com.example.TTTotNghiep.model;

import com.example.TTTotNghiep.Response.OrderResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

    private LocalDateTime orderDate;
    private String receiver;
    private String addressDescription;
    private String description;
    @JoinColumn(name = "statuss")
    private Integer statuss; //0: Cancel, 1: Waiting for approve, 2: In process, 3: Done
    @JoinColumn(name = "number_phone")
    private String numberPhone;
    @JoinColumn(name = "typee")
    private Integer typee;//1: order, 0: cart


    @ManyToOne
    @JoinColumn(name = "CommuneID")
    private Commune commune;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private User orderer;

    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    private User confirmer;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail>  orderDetails;

    public OrderResponse convertToResponse(){
        OrderResponse response = new OrderResponse();

        response.setId(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = orderDate.format(formatter);

        response.setOrderDate(formattedDate);
        response.setReceiver(receiver);
        response.setDescription(description);
        response.setStatusInt(getStatuss());
        switch (statuss){
            case 0: response.setStatus("Cancel"); break;
            case 1: response.setStatus("Waiting for approve");break;
            case 2: response.setStatus("In process");break;
            case 3: response.setStatus("Done");break;
        }
        //0: Cancel, 1: Waiting for approve, 2: In process, 3: Done
        response.setNumberPhone(numberPhone);
        response.setOrderer(orderer.getId() + "-" + orderer.getFullname());

        if(confirmer == null) {
            response.setConfirmer(null);
        }else{
            response.setConfirmer(confirmer.getId() + "-" + confirmer.getFullname());
        }

        //address
        String add = description +", "+ commune.getName()+", "+ commune.getMaqh().getName()+ ", "+ commune.getMaqh().getMatp().getName();
        response.setAddress(add);

        return response;
    }

}

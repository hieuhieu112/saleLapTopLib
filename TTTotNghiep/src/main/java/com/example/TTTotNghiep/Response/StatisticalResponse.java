package com.example.TTTotNghiep.Response;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@SqlResultSetMapping(
        name = "StatisticalResponseMapping",
        classes = @ConstructorResult(
                targetClass = StatisticalResponse.class,
                columns = {
                        @ColumnResult(name = "order_id", type = Integer.class),
                        @ColumnResult(name = "order_date", type = LocalDate.class),
                        @ColumnResult(name = "receiver", type = String.class),
                        @ColumnResult(name = "address_description", type = String.class),
                        @ColumnResult(name = "totalPrice", type = Float.class)
                }
        )
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "StatisticalResponse.getResponseOrder",
                query = "CALL GetSalesByDate(:start, :end)",
                resultSetMapping = "StatisticalResponseMapping"
        )
})
public class StatisticalResponse {
    @Id
    private Integer order_id;
    private LocalDate order_date;
    private String receiver;
    private String address_description;
    private Float totalPrice;

    public StatisticalResponse(Integer order_id, LocalDate order_date, String receiver, String address_description, Float totalPrice) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.receiver = receiver;
        this.address_description = address_description;
        this.totalPrice = totalPrice;
    }
}

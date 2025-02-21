package br.com.alelo.consumer.consumerpat.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ExtractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private int establishmentNameId;

    @Column
    private String establishmentName;

    @Column
    private String productDescription;

    @Column
    private Date dateBuy;

    @Column
    private Long cardNumber;

    @Column
    private BigDecimal amount;

}

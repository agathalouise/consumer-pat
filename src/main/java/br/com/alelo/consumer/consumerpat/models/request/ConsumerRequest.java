package br.com.alelo.consumer.consumerpat.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import br.com.alelo.consumer.consumerpat.models.AddressInfo;
import br.com.alelo.consumer.consumerpat.models.CardInfo;
import br.com.alelo.consumer.consumerpat.models.ContactInfo;


import java.util.Date;

@Data
public class ConsumerRequest {

    @Schema(description = "Número do documento do cliente")
    private String documentNumber;

    @Schema(description = "Nome do cliente")
    private String name;

    @Schema(description = "Data de nascimento do cliente")
    private Date birthDate;

    private ContactInfo contactInfo;

    private AddressInfo address;

    private CardInfo foodCard;
    private CardInfo fuelCard;
    private CardInfo drugstoreCard;

    //todo ver se a anotacao @Schema esta funcionando
}

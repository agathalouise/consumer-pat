package br.com.alelo.consumer.consumerpat.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import br.com.alelo.consumer.consumerpat.models.AddressInfo;
import br.com.alelo.consumer.consumerpat.models.ContactInfo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ConsumerRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Número do documento do cliente")
    private String documentNumber;

    @Schema(description = "Nome do cliente")
    private String name;

    @Schema(description = "Data de nascimento do cliente - yyyy-MM-dd ")
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private ContactInfo contactInfo;

    private AddressInfo address;

    private CardInfoRequest foodCard;
    private CardInfoRequest fuelCard;
    private CardInfoRequest drugstoreCard;

}

package br.com.alelo.consumer.consumerpat.enums;

import br.com.alelo.consumer.consumerpat.entities.ConsumerEntity;

import java.util.Objects;

public enum CardType {
    DRUGSTORE,
    FOOD,
    FUEL;

//    public static CardType fromCardNumber(ConsumerEntity consumerEntity, Long cardNumber) {
//        if (Objects.equals(consumerEntity.getDrugstoreCardNumber(), cardNumber)) return DRUGSTORE;
//        if (Objects.equals(consumerEntity.getFoodCardNumber(), cardNumber)) return FOOD;
//        if (Objects.equals(consumerEntity.getFuelCardNumber(), cardNumber)) return FUEL;
//        throw new IllegalArgumentException("Invalid card number: " + cardNumber);
//    }
}
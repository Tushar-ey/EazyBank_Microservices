package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardsDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ICardService {

    // create card  @Param-> Mobile Number of the customer

    void createCard(String mobileNumber);

    //  fetch card details @Param-> Mobile Number of the customer

    CardsDto fetchCard(String mobileNumber);

    // update card details
    boolean updateCard(CardsDto cardsDto);


    // delete card by mobile Number

    boolean deleteCard(String mobileNumber);


}

package com.eazybytes.cards.repository;

import com.eazybytes.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.smartcardio.Card;
import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {

   //--------Param-> mobileNumber   Response-> Optional Cards

   Optional<Cards> findByMobileNumber(String mobileNumber);

   Optional<Cards>  findByCardNumber(String mobileNumber);


   @Transactional
   @Modifying
  void deleteByMobileNumber(String mobileNumber);

}

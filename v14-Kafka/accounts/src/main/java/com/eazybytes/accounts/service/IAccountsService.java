package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountsService {

    // method to create an account
    void createAccount(CustomerDto customerDto);

    //method to fetch customer details based on mobile number
    CustomerDto fetchAccount(String mobileNumber);

    //method to update the resource
    // @param -> customerDto  Response-> boolean indication accounts or customer will be update or not
    boolean updateAccount(CustomerDto customerDto);

    // method to delete the resource by entering mobile number
    // @param -> mobile number         @Response-> boolean indicating delete or not

    boolean deleteAccount(String mobileNumber);

    boolean updateCommunicationStatus(Long accountNumber);


}

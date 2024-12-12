package com.eazybytes.accounts.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name = "native",strategy = "native")
    @Column(name="customer_id")
    private Long customerId;

    private String name;

    @Column(name="email")
    private String email;

    @Column(name="mobile_number")
    private String mobileNumber;




}

package com.acme.test01.tornikeshelia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingsAccount {

    private Long accountId;
    private Long customerNum;
    private BigDecimal balance;
    private Date creationDate;
    private Date updateDate;

}

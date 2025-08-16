# Problem Statement

1. Users need to register on the service to use this wallet
2. User can load money into their wallet using various sources (credit card, debit card, UPI etc)
a. minimum amount of load money should be greater than zero
b. no need to integrate with other sources, can simply send success status
3. User can send money from their wallet to other users
a. minimum amount of transaction will be greater than 0
b. sufficient balance in wallet should be ensured before making transaction
4. User should be able to fetch wallet balance
5. User can get transaction history based on:
a. sort transaction history by amount or timestamp
b. filter transaction history by send/receive
c. assumption: consider 1 filter at a time.
# ATM System - Low-Level Design (LLD)

The ATM System is a software application that simulates the functionalities of an Automated Teller Machine (ATM). This system is designed to demonstrate the low-level design of an ATM using object-oriented principles.

## Architecture Overview

The ATM System follows a layered architecture, comprising the following layers:

- **User Interface Layer:** This layer is responsible for interacting with the user, capturing input, and displaying information. It includes the `Main` class, which serves as the entry point for the system and handles user interactions through the command-line interface.

- **Controller Layer:** This layer contains the controllers that handle user requests and orchestrate the interactions between different components of the system. It includes the `ATM` class, which manages the overall ATM operations, such as cash reserve management and user transactions.

- **Model Layer:** This layer consists of the data models and entities used in the system. It includes classes like `AtmCard`, `CashReserve`, and `Transaction` that represent ATM cards, cash reserves, and user transactions, respectively.

- **Strategy Layer:** This layer encapsulates different strategies for cash dispensing. It includes the `CashDispenserStrategy` interface and its implementation, such as the `SimpleCashDispenseStrategy`, which defines the logic for dispensing cash based on the available cash reserve.

- **Enums:** This package contains different enumerations used in the system, such as `Denomination` for representing various currency denominations and `PaymentNetwork` for defining payment network types.

- **Models Package:** This package contains the concrete implementations of ATM cards, such as `AxisVisaCard` and `SbiRupayCard`, which extend the `AtmCard` class and define card-specific details like the bank and payment network.

## Features and Functionality

The ATM System supports the following features:

- **User Login:** The system allows users to log in using an ATM card, currently implemented with dummy card details.

- **Cash Withdrawal:** Users can withdraw cash from the ATM. The system verifies the PIN and checks the cash availability before dispensing the requested amount.

- **PIN Change:** Users can change the PIN associated with their ATM card.

- **Balance Inquiry:** Users can check the balance of their linked bank account.

- **Mini Statement:** Users can request a mini statement of their recent transactions.

- **Admin Login:** The system provides an admin login option to manage the cash reserve. Only authorized administrators can modify the cash reserve.

## Getting Started

To run the ATM System, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Build the project using Gradle to resolve dependencies. You can use the following command in the terminal or command prompt:
   ./gradlew build
4. Run the `Main` class to start the ATM system.
5. Follow the instructions displayed in the console to perform different operations.
- For user login, enter `1`.
- For admin login, enter `2`. Use the password "admin12345" for admin login.

## Extensibility and Customization

The ATM System is designed to be extensible and customizable. You can add new features or modify existing functionalities by extending or implementing the relevant classes and interfaces. For example, you can introduce new types of ATM cards, implement additional cash dispensing strategies, or integrate the system with real banking services.

## Note

The ATM System is a simulated application and does not interact with real banks or handle actual monetary transactions. It serves as a demonstration of low-level design concepts and can be further developed for practical usage.
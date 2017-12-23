# CustomerBase 1.0

    CustomerBase is an application that allows a company to see their customer information.
    Customers have the following information associated with them:
        ● Unique customer identifier.
        ● Status: one of "prospective", "current" or "non-active".
        ● Creation date and time.
        ● General information like name and contact details.
    The company can also make notes for each customer. A customer can have any number of  notes associated with them.
    
    The user is able to:
        ● Filter and sort the list of customers.
        ● Click on a customer in the list to view their details and add/edit notes for that customer.
        ● Change their status.

## Dependencies

The application requires JRE 8 for its backend, MySQL to manage the data and bootstrap for the UI.

## Components

The application has 3 components:<br />
    ● Backend:<br />
        - Written in JAVA.<br />
        - Exposes a REST api.<br />
        - Connects to a MySQL server to read and write data.<br />
    ● Frontend:<br />
        - Written in HTML.<br />
        - Uses the backend's REST API.<br />
    ● MySQL.<br />
        - Manages the data on the disk.<br />

## Installation

1. Setup MySQL:<br />
    ```bash
    $ sudo apt-get install mysql-server
    ```
    <br />
    Run exec.sql to create the required database and tables. Create a user with read and write access to that database.
	
2. Install JRE8:<br />
	```bash
	$ sudo apt-get install default-jre
	```
	
3. Run the backend software:<br />
    Edit run.sh from bin/backend with your desired database information and port number.<br />
    The command line parameters are:<br />
        ● dbuser: MySQL database user.<br />
        ● dbpass: MySQL database password.<br />
        ● dbuser: MySQL database URL.<br />
        ● db: MySQL database name.<br />
        ● port: The port number for the REST API.<br />

	```bash
    cd bin/backend
	$ ./run.sh
	```

4. Install apache or nginx:<br />
	```bash
	$ sudo apt-get install apache2    
	```

5. Copy frontend to apache or nginx working directory:<br />
	```bash
	$ sudo cp bin/frontend/* /var/www  
	```

    By default, the frontend will try to connect to the backend on port 4080. If the port is changed modify customerBase.html at line 421.

## Tests

    To test the stack, run src/test/test.html on the same machine as the backend. The test.html page assumes the backend has been configured to use port 4080.

## Usage

Navigate to IP_ADDRESS/customerBase.html

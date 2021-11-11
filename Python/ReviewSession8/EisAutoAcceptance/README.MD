## Script for automatic matching of payments with charges in the work system.

### Description of the problem:
Manual matching of payments with charges in the work system.

### Consequences of the problem:
- A lot of routine and drab work;
- The volume of work is constantly increasing.

### Solution to the problem:
Automation of manual operation using a Python script with the Selenium library.

### Script can do:
- Log in and click on the desired elems (Selenium);
- Receive information about payments and charges (BeautifulSoup, lxml);
- Receive data from registry XML payments (lxml);
- Display the progress of work (Progress);
- Save information in SQLite database (Peewee);
- Parallel matching of payments (Multiprocessing.Pool);
- Create a report on the discrepancy between payer and debtor (XlsxWriter).

### Result of using the script:
- Automation of operations for all payments with data on charges;
- No errors: matching payments with correct charges.

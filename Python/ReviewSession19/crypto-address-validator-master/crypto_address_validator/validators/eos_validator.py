import re


def is_valid_address(address: str) -> bool:
    """
    Validates the passed eos address.
    Args:
        address (str): Currency address to validate.

    Returns:
        bool: Result of address validation.
    """
    # SEE https://developers.eos.io/welcome/v2.1/protocol-guides/accounts_and_permissions/#2-accounts
    address_regex = r'^[a-z1-5.]{1,12}(?<!\.)$'

    if not re.match(address_regex, address):
        return False

    return True

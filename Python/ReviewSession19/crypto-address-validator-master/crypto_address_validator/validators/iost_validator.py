import re


def is_valid_address(address: str) -> bool:
    """
    Validates the passed iost address.
    Args:
        address (str): Currency address to validate.

    Returns:
        bool: Result of address validation.
    """
    # SEE https://developers.iost.io/docs/en/2-intro-of-iost/Account.html
    address_regex = r'^[a-z0-9_.]{5,11}(?<!\.)$'

    return True if re.match(address_regex, address) else False

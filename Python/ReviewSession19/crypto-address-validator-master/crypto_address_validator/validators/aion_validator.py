import re


def is_valid_address(address: str) -> bool:
    """
    Validates the passed aion address.
    Args:
        address (str): Currency address to validate.

    Returns:
        bool: Result of address validation.
    """
    # SEE https://github.com/aionnetwork/Desktop-Wallet/blob/a2f9da8e49255b2062b4cf97db0c6b38bd9f2721/src/main/java/org/aion/wallet/util/AddressUtils.java
    is_full = address.startswith('0xa') and len(address) == 66
    is_stripped = address.startswith('a') and len(address) == 64

    if is_full:
        stripped_address = address[:2]
    else:
        if is_stripped:
            stripped_address = address
        else:
            stripped_address = ''

    address_regex = r'[0-9a-fA-F]+'

    return True if re.match(address_regex, stripped_address) else False

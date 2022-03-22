from . import default_validator


def is_valid_address(address: str) -> bool:
    """
    Validates the passed atom address.
    Args:
        address (str): Currency address to validate.

    Returns:
        bool: Result of address validation.
    """
    # SEE https://docs.cosmos.network/master/spec/addresses/
    return default_validator._bech32_decode(address)

from . import default_validator as df


def is_valid_address(address: str) -> bool:
    """
    Validates the passed bnb address.
    Args:
        address (str): Currency address to validate.

    Returns:
        bool: Result of address validation.
    """
    # SEE https://docs.binance.org/blockchain.html#cryptographic-design
    return df._bech32_decode(address)

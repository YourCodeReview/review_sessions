from . import default_validator as df
import re


def is_valid_address(address: str) -> bool:
    """
    Validates the passed miota address.
    Args:
        address (str): Currency address to validate.

    Returns:
        bool: Result of address validation.
    """
    # SEE https://github.com/iotaledger/iota.crypto.js/blob/5450ce54c8d9f46c3ff2d5bea2c48d9e1021da08/lib/utils/inputValidator.js
    expected_prefixes = ('iota', 'atoi')            # mainnet, devnet

    if address[:4] in expected_prefixes:            # Chrysalis address format
        return df._bech32_decode(address)

    else:                                           # Legacy address format
        if len(address) == 90:                      # Check if address with checksum
            return True if is_trytes(address, 90) else False

        elif len(address) == 81:
            return True if is_trytes(address, 81) else False


def is_trytes(trytes: str, length: int = 0) -> bool:
    """Checks if input is correct trytes consisting of A-Z9 optionally validate length.
    If no length specified, just validate the trytes.

    Args:
        trytes (str):
        length (int):

    Returns:
        bool:
    """
    regex_trypes = '^[9A-Z]{' + str(length) + '}$'

    return True if re.match(regex_trypes, trytes) else False

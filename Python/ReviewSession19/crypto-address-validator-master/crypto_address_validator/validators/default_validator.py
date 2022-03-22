import binascii
import hashlib
import base58
from crypto_address_validator.validators.crypto import bech32m


def is_valid_address(address: str) -> bool:
    """
    Validates the passed btc address.
    Args:
        address (str): Currency address to validate.

    Returns:
        bool: Result of address validation.
    """
    return _base58_decode(address) or _bech32_decode(address)


def _base58_decode(address: str) -> bool:
    """
    SEE https://en.bitcoin.it/wiki/Base58Check_encoding
    """
    try:
        decoded_address = base58.b58decode(address).hex()
        result, checksum = decoded_address[:-8], decoded_address[-8:]

    except ValueError:
        return False

    else:
        for _ in range(1, 3):
            result = hashlib.sha256(binascii.unhexlify(result)).hexdigest()

        return checksum == result[:8]


def _bech32_decode(address: str) -> bool:
    """
    SEE https://github.com/bitcoin/bips/blob/1f0b563738199ca60d32b4ba779797fc97d040fe/bip-0350.mediawiki
    """
    decoded_address = bech32m.bech32_decode(address)

    if None in decoded_address:
        return False

    return True

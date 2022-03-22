# Cryptro address validator
Simple validation tool for Bitcoin and other altcoin addresses.

## Supported currencies
| Currency      | Symbol | Mainnet | Testnet    | Note                                                                                                      |
|:-------------:| ------ | ------- | ---------- | ---------------------------------------------------------------------------------------------         |
| Bitcoin       | BTC    | +       | +          | P2PKH (Legacy Adresses), P2SH (Pay to Script Hash), P2WPKH (Native SegWit), P2TR (Taproot) address formats    |
| Cosmos        | ATOM   | +       | -          |                                                                                                       |
| Binance Coin  | BNB    | +       | +          |                                                                                                       |
| Aion          | AION   | +       | +          |                                                                                                       |
| EOS           | EOS    | +       | +          |                                                                                                       |
| IOST          | IOST   | +       | +          |                                                                                                       |
| IOTA          | MIOTA  | +       | Devnet     | Chrysalis, Legacy address formats                                                                                                                                                 |

## Installation
```
pip install crypto_address_validator
```

## Usage
```python
from crypto_address_validator import validate

validate('btc', 'bc1q7w9p2unf3hngtzmq3sq47cjdd0xd9sw7us5h6p')
```

## License
The Unlicense. See the LICENSE file for details.

import * as React from 'react';
import { Box } from 'devtools/react-controls/box';

const style: React.CSSProperties = { textAlign: 'center', color: 'gray', fontSize: '30px' };

export const Header: React.FC = () => (
  <Box style={style} padding>
    Network logger
  </Box>
);

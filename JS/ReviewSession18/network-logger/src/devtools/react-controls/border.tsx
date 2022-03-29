import * as React from 'react';
import * as bc from 'devtools/styles/base';
import styled from 'styled-components';

const borderStyle = `1px solid ${bc.hoveredBorder}`;

const StyledDiv = styled.div<Props>`
  border-left: ${p => p.left && borderStyle};
  border-right: ${p => p.right && borderStyle};
  border-top: ${p => p.top && borderStyle};
  border-bottom: ${p => p.bottom && borderStyle};
`;

interface Props {
  left?: boolean;
  right?: boolean;
  top?: boolean;
  bottom?: boolean;
}

export const Border: React.FC<Props> = props => (
  <StyledDiv {...props}>
    {props.children}
  </StyledDiv>
);

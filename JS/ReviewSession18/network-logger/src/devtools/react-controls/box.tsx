import * as React from 'react';
import * as bc from 'devtools/styles/base';
import styled from 'styled-components';

type Margin = true | `${number}px`;

interface StyledProps extends Omit<Props, 'spacing' | 'overflow'> {
  spacingM?: Props['spacing'];
  overflowM?: Props['overflow'];
}

const StyledDiv = styled.div<StyledProps>`
  display: flex;
  flex-grow: ${p => p.grow ? 1 : 0};
  flex-direction: ${p => p.row ? 'row' : 'column'};
  & > *:not(:first-child) {
    margin-top: ${p => p.col && getMarginValue(p)};
    margin-left: ${p => p.row && getMarginValue(p)};
  }
  padding-left: ${p => (p.padding == 'h' || p.padding === true) && bc.spacing};
  padding-right: ${p => (p.padding == 'h' || p.padding === true) && bc.spacing};
  padding-top: ${p => (p.padding == 'v' || p.padding === true) && bc.spacing};
  padding-bottom: ${p => (p.padding == 'v' || p.padding === true) && bc.spacing};
  justify-content: ${p => getJustifyContentValue(p)};
  margin-left: ${p => p.marginLeft === true ? 'auto' : p.marginLeft};
  margin-right: ${p => p.marginRight === true ? 'auto' : p.marginRight};
  margin-top: ${p => p.marginTop === true ? 'auto' : p.marginTop};
  margin-bottom: ${p => p.marginBottom === true ? 'auto' : p.marginBottom};
  overflow: ${p => p.overflowM === true ? 'auto' : undefined};
  overflow-x: ${p => p.overflowM === 'x' ? 'auto' : undefined};
  overflow-y: ${p => p.overflowM === 'y' ? 'auto' : undefined};
`;

interface Props extends React.HTMLAttributes<HTMLDivElement> {
  row?: boolean;
  col?: boolean;
  grow?: boolean;
  spacing?: boolean;
  spacingSm?: boolean;
  padding?: 'v' | 'h' | true;
  marginLeft?: Margin;
  marginRight?: Margin;
  marginTop?: Margin;
  marginBottom?: Margin;
  justifyContent?: 'start' | 'center' | 'end';
  overflow?: 'x' | 'y' | true;
}

export const Box: React.FC<Props> = props => {
  const { spacing, overflow, ...rest } = props;
  return (
    <StyledDiv spacingM={spacing} overflowM={overflow} {...rest}>
      {props.children}
    </StyledDiv>
  );
};

function getJustifyContentValue(p: Props) {
  return p.justifyContent == 'start'
    ? 'flex-start'
    : p.justifyContent == 'end'
      ? 'flex-end'
      : undefined;
}

function getMarginValue(props: StyledProps) {
  return (props.spacingM && bc.spacing) || (props.spacingSm && bc.spacingSm);
}

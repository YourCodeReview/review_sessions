import * as React from 'react';
import * as bc from 'devtools/styles/base';
import styled from 'styled-components';

interface StyledProps extends React.InputHTMLAttributes<HTMLInputElement> {
  grow?: boolean;
}

export const StyledInput = styled.input<StyledProps>`
  background-color: ${bc.controlBgColor};
  color: ${bc.textColor};
  border: 1px solid transparent;
  padding: 1px;
  flex-grow: ${p => p.grow ? Number(p.grow) : 0};

  :focus {
    border-color: ${bc.focusedBorder};
  }

  :hover {
    border-color: ${bc.hoveredBorder};
  }
`;

const enum KeyCodes {
  ENTER = 13
}

interface Props extends Omit<StyledProps, 'onChange'> {
  onEnter?: (value: string) => void;
  onChange?: (value: string) => void;
}

export class TextInput extends React.Component<Props> {
  private onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    this.props.onChange?.(e.target.value);
  };

  private onEnter = (text: string) => {
    this.props.onEnter?.(text);
  };

  private onKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.keyCode == KeyCodes.ENTER && this.props.onEnter) {
      e.preventDefault();
      this.onEnter(e.currentTarget.value);
    }
    this.props.onKeyDown?.(e);
  };

  render() {
    const { value, onChange, ...props } = this.props;

    return (
      <StyledInput
        value={value}
        type='text'
        {...props}
        onChange={this.onChange}
        onKeyDown={this.onKeyDown}
      />
    );
  }
}

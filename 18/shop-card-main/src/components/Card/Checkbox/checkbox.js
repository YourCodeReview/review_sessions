import { useState } from "react";
import styleComponent from "./style.module.css";

const CheckBox = ({ volume }) => {
  const [isChecked, setChecked] = useState(false);
  const checkActive = `${styleComponent.custCheckboxActive}`;

  return (
    <div className={styleComponent.container}>
      <label className={styleComponent.desc}>
        <input type="checkbox" onClick={() => setChecked(!isChecked)} />
        <span
          className={`${styleComponent.custCheckbox} ${
            isChecked ? checkActive : ""
          }`}
        ></span>
        {volume}
      </label>
    </div>
  );
};

export default CheckBox;

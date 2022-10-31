import { css } from "@emotion/react";

const style = css`
  color: hotpink;
`;

const Popup = () => {
  return (
    <div css={style}>
      <div>hello world</div>
      <div>fuck you</div>
    </div>
  );
};

export default Popup;

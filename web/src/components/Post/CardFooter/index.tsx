import React from "react";
import { ThumbUp } from "@material-ui/icons";

import { Styles } from "./styles";

function CardFooter() {
  return (
    <Styles>
      <ThumbUp fontSize="small" />
      <span>4</span>
    </Styles>
  );
}

export default CardFooter;

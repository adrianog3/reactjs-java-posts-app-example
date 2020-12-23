import React from "react";
import ReactDOM from "react-dom";
import Layout from "./pages/Layout";
import { GlobalStyle } from "./styles/global";
import { BrowserRouter } from "react-router-dom";
import { SnackbarProvider } from "notistack";

ReactDOM.render(
  <>
    <SnackbarProvider
      maxSnack={3}
      anchorOrigin={{
        vertical: "bottom",
        horizontal: "right",
      }}
    >
      <BrowserRouter>
        <GlobalStyle />
        <Layout />
      </BrowserRouter>
    </SnackbarProvider>
  </>,
  document.getElementById("root")
);

import React from "react";
import Header from "../Header";
import Post from "../Post";
import Sidebar from "../Sidebar";
import { SidebarProvider } from "../../context/Sidebar";
import { StylesWrapper } from "./styles";

function App() {
  return (
    <SidebarProvider>
      <StylesWrapper>
        <Sidebar />
        <Header />
        <div className="content-wrapper">
          <Post />
        </div>
      </StylesWrapper>
    </SidebarProvider>
  );
}

export default App;

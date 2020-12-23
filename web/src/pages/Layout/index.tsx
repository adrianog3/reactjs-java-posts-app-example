import React from "react";
import Header from "../../components/Header";
import Sidebar from "../../components/Sidebar";
import { SidebarProvider } from "../../context/Sidebar";
import { Styles } from "./styles";
import Routes from "../../routes";

const Layout: React.FC = () => {
  return (
    <SidebarProvider>
      <Styles>
        <Sidebar />
        <Header />
        <main>
          <Routes />
        </main>
      </Styles>
    </SidebarProvider>
  );
};

export default Layout;

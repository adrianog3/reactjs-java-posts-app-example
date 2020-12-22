import React, { createContext, useState, useContext } from "react";

const SidebarContext = createContext<ContextProps>({
  open: false,
  setOpen: (_: boolean) => console.log("No function (setOpen) was provided"),
});

type ContextProps = {
  open: boolean;
  setOpen: (value: boolean) => void;
};

export function SidebarProvider({ children }: any) {
  const [open, setOpen] = useState<boolean>(false);

  return (
    <SidebarContext.Provider value={{ open, setOpen }}>
      {children}
    </SidebarContext.Provider>
  );
}

export default function useSidebar(): ContextProps {
  const context = useContext<ContextProps>(SidebarContext);

  const { open, setOpen } = context;

  return { open, setOpen };
}

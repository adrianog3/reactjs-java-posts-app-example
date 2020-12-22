import { AppBar, IconButton, Toolbar, Typography } from "@material-ui/core";
import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
import MenuIcon from "@material-ui/icons/Menu";
import clsx from "clsx";
import React from "react";
import useSidebar from "../../context/Sidebar";
import { Styles } from "./styles";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    menuButton: {
      marginRight: theme.spacing(2),
    },
    hide: {
      display: "none",
    },
  })
);

function Header() {
  const classes = useStyles();
  const { open, setOpen } = useSidebar();

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  return (
    <Styles>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            className={clsx(classes.menuButton, open && classes.hide)}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h5">Blog - Example</Typography>
        </Toolbar>
      </AppBar>
    </Styles>
  );
}

export default Header;

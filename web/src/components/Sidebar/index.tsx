import CssBaseline from "@material-ui/core/CssBaseline";
import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
import IconButton from "@material-ui/core/IconButton";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
import { AddBox, Home } from "@material-ui/icons";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import React from "react";
import useSidebar from "../../context/Sidebar";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    drawerPaper: {
      width: 230,
      fontSize: 30,
    },
    drawerHeader: {
      display: "flex",
      alignItems: "center",
      padding: theme.spacing(0, 1),
      ...theme.mixins.toolbar,
      justifyContent: "flex-end",
    },
    multiline: {
      fontSize: "0.52em",
    },
  })
);

export default function PersistentDrawerLeft() {
  const classes = useStyles();
  const { open, setOpen } = useSidebar();

  const handleDrawerClose = () => {
    setOpen(false);
  };

  function handleHomeClick() {
    setOpen(false);
  }

  return (
    <div>
      <CssBaseline />
      <Drawer
        variant="persistent"
        anchor="left"
        open={open}
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <div className={classes.drawerHeader}>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <Divider />
        <List>
          <ListItem button key="1" onClick={handleHomeClick}>
            <ListItemIcon>
              <Home fontSize="small" />
            </ListItemIcon>
            <ListItemText
              primary="Home"
              classes={{ primary: classes.multiline }}
            />
          </ListItem>
          <ListItem button key="2">
            <ListItemIcon>
              <AddBox fontSize="small" />
            </ListItemIcon>
            <ListItemText
              primary="Cadastrar Post"
              classes={{ primary: classes.multiline }}
            />
          </ListItem>
        </List>
      </Drawer>
    </div>
  );
}

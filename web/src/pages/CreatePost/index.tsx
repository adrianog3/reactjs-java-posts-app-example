import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import React from "react";
import { useForm } from "react-hook-form";
import { savePost, IPost } from "../../services/post";
import { useSnackbar } from "notistack";
import { useHistory } from "react-router-dom";

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function CreatePost() {
  const classes = useStyles();
  const { enqueueSnackbar } = useSnackbar();
  const { register, handleSubmit } = useForm<IPost>();
  const history = useHistory();

  const onSubmit = async (data: IPost) => {
    const message = await savePost(data);

    if (message) {
      enqueueSnackbar(message, { variant: "error" });
    } else {
      enqueueSnackbar("Postagem criada com sucesso", { variant: "success" });
      history.push("/");
    }
  };

  return (
    <Container component="main" maxWidth="sm">
      <div className={classes.paper}>
        <form className={classes.form} noValidate onSubmit={handleSubmit(onSubmit)}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                inputRef={register}
                variant="outlined"
                required
                fullWidth
                id="author"
                label="Autor da postagem"
                name="author"
                autoComplete="email"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                inputRef={register}
                variant="outlined"
                required
                fullWidth
                name="title"
                id="title"
                label="Título da Postagem"
                autoComplete="current-password"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                inputRef={register}
                variant="outlined"
                required
                fullWidth
                id="text"
                name="text"
                label="Texto da Postagem"
                autoComplete="current-password"
              />
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            style={{ height: 40 }}
          >
            CRIAR POSTAGEM
          </Button>
        </form>
      </div>
    </Container>
  );
}

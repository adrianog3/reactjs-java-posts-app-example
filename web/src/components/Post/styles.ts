import styled from "styled-components";

export const Styles = styled.div`
  .post {
    padding: 1rem 1rem 1rem 1rem;
    margin-bottom: 0.8rem;
  }

  .post h1,
  p {
    text-align: left;
  }

  .header {
    display: flex;
    align-items: center;
    justify-content: left;
  }

  .header span {
    padding-left: 0.8rem;
  }

  .card-title {
    display: flex;
    flex-direction: column;
  }

  .card-title .title {
    padding-bottom: 0.25rem;
    font-size: 0.95rem;
  }

  .card-title .subtitle {
    color: #757575;
    font-size: 0.9rem;
  }

  .footer {
    display: flex;
    justify-content: left;
    align-items: center;
  }

  span {
    padding-left: 0.65rem;
    font-size: 0.9rem;
  }
`;

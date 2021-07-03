import "./App.css";
import Login from "./Login";
import { getTokenFromUrl } from "./spotify";
import React, { useEffect, useState } from "react";
import SpotifyWebApi from "spotify-web-api-js";
import Player from "./Player";
import { useDataLayerValue } from "./DataLayer";

const spotify = new SpotifyWebApi();

//use effect hooks - allow you to run code when component load and rerun it when certain things change
function App() {
  const [token, setToken] = useState(null);
  const [{ user }, dispatch] = useDataLayerValue();
  //run code based on a given condition in brackets
  useEffect(() => {
    //code....
    const hash = getTokenFromUrl();
    window.location.hash = "";
    const _token = hash.access_token;
    
    if (_token) {
      setToken(_token);

      spotify.setAccessToken(_token);

      spotify.getMe().then((user) => {
        dispatch({
          type: "SET_USER",
          user: user,
        });
      });
    }
    console.log("I have a token>>>>>>>>>>>>>>> ", token);
  }, []);

  console.log("user: ", user);

  return <div className="app">{token ? <Player /> : <Login />}</div>;
}

export default App;

document.addEventListener("DOMContentLoaded", function() {
    let tokenElement = document.querySelector(".token");
    console.log("tokenElement"+tokenElement);
    let accessToken = tokenElement.textContent.trim();
    let artistname=document.querySelector(".artistname");
    let songscontainer=document.querySelector("#songs-container");
    console.log("tokenElement"+accessToken);
    let offset = 0;
    let limit = 20;
    let device_id = null;
    let artist;
  let submit_btn = document.querySelector(".submit_btn");
  
 
    if (accessToken) {
        console.log('Access Token:', accessToken);
        fetchAlbums(offset, limit);

        document.getElementById('load-more').addEventListener('click', function() {
            offset += limit;
            fetchAlbums(offset, limit,artist);
        });

        // Initialize Spotify Player
        window.onSpotifyWebPlaybackSDKReady = () => {
            const player = new Spotify.Player({
                name: 'Web Playback SDK Quick Start Player',
                getOAuthToken: cb => { cb(accessToken); }
            });

            // Error handling
            player.addListener('initialization_error', ({ message }) => { console.error(message); });
            player.addListener('authentication_error', ({ message }) => { console.error(message); });
            player.addListener('account_error', ({ message }) => { console.error(message); });
            player.addListener('playback_error', ({ message }) => { console.error(message); });

            // Playback status updates
            player.addListener('player_state_changed', state => { console.log(state); });

            // Ready
            player.addListener('ready', ({ device_id: id }) => {
                console.log('Ready with Device ID', id);
                device_id = id;
            });

            // Not Ready
            player.addListener('not_ready', ({ device_id: id }) => {
                console.log('Device ID has gone offline', id);
            });

            player.connect();
        };
    } else {
        console.error('Access token not found');
    }
    
     submit_btn.addEventListener("click", (e) => {
        e.preventDefault();
        artist=artistname.value;
        console.log(artist);
        fetchAlbums(offset, limit, artist);
    });

    function fetchAlbums(offset, limit,artist) {
		if(artist!=null){
		songscontainer.innerHTML="";
		}
		
        fetch(`https://api.spotify.com/v1/search?q=${artist}&type=album&offset=${offset}&limit=${limit}`, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch albums');
            }
            return response.json();
        })
        .then(data => {
            displayAlbums(data.albums.items);
            console.log('Albums Data:', data);
        })
        .catch(error => console.error('Error:', error));
    }

    function displayAlbums(albums) {
        let container = document.getElementById('songs-container');
        albums.forEach(album => {
            let albumElement = document.createElement('div');
            albumElement.classList.add('album');
            
            let albumInfo = `
                <h2>${album.name}</h2>
                <img src="${album.images[0].url}" alt="${album.name}" width="200">
                <p>Artist: ${album.artists.map(artist => artist.name).join(', ')}</p>
                <p>Release Date: ${album.release_date}</p>
                <a href="${album.external_urls.spotify}" target="_blank">Listen on Spotify</a>
                <div id="tracks-${album.id}" class="tracks-container"></div>
            `;
            
            albumElement.innerHTML = albumInfo;
            container.appendChild(albumElement);

            fetch(`https://api.spotify.com/v1/albums/${album.id}/tracks`, {
                headers: {
                    'Authorization': `Bearer ${accessToken}`
                }
            })
            .then(response => response.json())
            .then(tracksData => {
                let tracksContainer = document.getElementById(`tracks-${album.id}`);
                let tracksList = document.createElement('ul');
                
                tracksData.items.forEach(track => {
                    let trackItem = document.createElement('li');
                    trackItem.innerHTML = `
                        ${track.name}
                        <button onclick="playTrack('${track.uri}')">Play</button>
                    `;
                    tracksList.appendChild(trackItem);
                });
                tracksContainer.appendChild(tracksList);
            })
            .catch(error => console.error('Error fetching tracks:', error));
        });
    }

    window.playTrack = function(trackUri) {
        if (!device_id) {
            console.error('Device ID not available');
            return;
        }

        fetch(`https://api.spotify.com/v1/me/player/play?device_id=${device_id}`, {
            method: 'PUT',
            body: JSON.stringify({ uris: [trackUri] }),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${accessToken}`
            },
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to play track');
            }
        })
        .catch(error => console.error('Error playing track:', error));
    };
});

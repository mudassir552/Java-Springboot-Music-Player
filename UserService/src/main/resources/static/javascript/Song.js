let SongsContainer = document.querySelector('.SongsContainer');
let inner = document.querySelector('.inner');
let profileImage = document.querySelector('.profileImg');
let modal = document.querySelector('.exampleModalCenter');
let input = document.querySelector('.input');
let songmodal =document.querySelector('#songmodal');
let user_img = document.querySelector('.user_img');
let submit_btn = document.querySelector('.btn');
let Accountdetails = document.querySelector('.Account_details');
let modal_body = document.querySelector('.modal-body-h3');
let error = document.querySelector('.error');
let searchContainer = document.querySelector('.search-btn');
let exampleModalCenter=document.getElementById("#exampleModalCenter");
let pageElementsSize = 15;
let page = 0;
let loader=document.querySelector(".loader");
let innerContainer=document.querySelector(".innerContainer");

const Userapi = "http://localhost:8080/UserImage";

//const Userapi = "http://user-service:8080/UserImage";

//const Userapi=USER_API_URL;
const getImageapi = "http://localhost:8080/ProfileImage";

//const getImageapi = "http://user-service:8080/ProfileImage";
//let fetchUrl, getImageapi, Userapi,createsongs;
let createsongs="http://localhost:8080/createsongs";

document.addEventListener('DOMContentLoaded', async () => {
    //await fetchConfiguration();
    if(SongsContainer.innerHTML==""){
		
		loader.style.display="block";
		
	}
    fetchDataAndUpdateUI(page, pageElementsSize);
    getprofileImage()
    //setupEventListeners();
});

async function fetchConfiguration() {
    try {
        const response = await fetch('/config');
        const config = await response.json();
        fetchUrl = config.FETCH_URL;
        getImageapi = config.IMAGE_API_URL;
        Userapi = config.USER_API_URL;
        createsongs = config.CREATE_SONGS;

        console.log(`Fetch URL: ${fetchUrl}`);
        console.log(`Image API URL: ${getImageapi}`);
        console.log(`User API URL: ${Userapi}`);
        console.log(`Create songs: ${createsongs}`);
    } catch (error) {
        console.error('Error fetching configuration:', error);
    }
}


//console.log("outside fetchurl"+fetchUrl);
console.log("outside getimageapi"+getImageapi);
console.log("outside Userapi"+Userapi);
//console.log("outside createsongs"+createsongs);
//const getImageapi=IMAGE_API_URL;
let SongsObject = inner.getAttribute('Song');
let Songs = JSON.parse(SongsObject);

let totalPages = Songs.totalPages;

console.log("totalpages"+totalPages);
let currentpage = Songs.currentPage;

user_img.addEventListener('mouseover', () => {
    Accountdetails.style.display = 'block';
});

// Hide the account details on mouse out
user_img.addEventListener('mouseout', () => {
    Accountdetails.style.display = 'none';
});

// For touch devices, you might want to toggle the display on touch start
user_img.addEventListener('touchstart', () => {
    if (Accountdetails.style.display === 'block') {
        Accountdetails.style.display = 'none';
    } else {
        Accountdetails.style.display = 'block';
    }
});

function openFileDialog() {
    const fileInput = document.getElementById('fileInput');
    fileInput.click();
}

function handleFileSelect(event) {
    const file = event.target.files[0];
    uploadImage(file);
}

function uploadImage(file) {
    const formData = new FormData();
    formData.append('image', file);
   //Userapi+'/UserImage'
    fetch(Userapi, {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(data => {
        localStorage.removeItem('profileImageData');
        localStorage.setItem('profileImage', data);
        user_img.src = `data:image/jpeg;base64,${data}`;
        getprofileImage();
    })
    .catch(error => {
        console.error('There was a problem with the upload:', error);
    });
}

function displayCachedProfileImage() {
    const cachedImageData = localStorage.getItem('profileImageData');
    const updatedImageData = localStorage.getItem('profileImage');
    if (cachedImageData) {
        user_img.src = `data:image/jpeg;base64,${cachedImageData}`;
        return true;
    }
    else if (updatedImageData) {
        user_img.src = `data:image/jpeg;base64,${updatedImageData}`;
        return true;
    }
    else {
        return false;
    }
}

function getprofileImage() {
	
	console.log("inside profile image "+getImageapi+'/ProfileImage');
	
	//getImageapi+'/ProfileImage'
    fetch(getImageapi, {
        method: 'GET',
    })
    .then(response => {
		
		
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(data => {
        user_img.src = `data:image/jpeg;base64,${data}`;
        localStorage.setItem('profileImageData', data);
    })
    .catch(error => {
        console.error('There was a problem with the upload:', error);
    });
}

if (!displayCachedProfileImage()) {
    getprofileImage();
}

Accountdetails.addEventListener('mouseover', () => {
    Accountdetails.style.display = 'block';
});

Accountdetails.addEventListener('mouseout', () => {
    Accountdetails.style.display = 'none';
});

let index = 0;
function addLastCardelem(Songs) {
     let html = '<div class="row">';
     
    for (let i = 0; i < Songs.songs.length; i++) {
		  index=i;
		console.log(Songs.songs[i]);
        html += `
            <div class="card" data-index="${i}">
                <div class="card-body">
                <i class="material-icons" onClick="deleteSong(${i})" style="font-size: 20px; color: red; position: relative; top: 10px; right: 20px;">delete</i>
                    <p  style="font-family: 'Roboto', sans-serif; margin-bottom:30px;">${Songs.songs[i].song}</p>
                    <p style="font-family: 'Roboto', sans-serif;margin-bottom:30px;">${Songs.songs[i].artist}</p>
                    <img class="Songimage" src="data:image/png;base64,${Songs.songs[i].image}" alt="Song Image">
                    <audio controls class="audio" preload="metadata">
                        <source src="data:audio/mpeg;base64,${Songs.songs[i].songFile}" type="audio/mpeg">
                        Your browser does not support the audio element.
                    </audio>
                </div>
            </div>
        `;
    }
    html += `
        <div  id="last-card">
        <div class="card" id="last-card" style="margin-bottom:0px;">
            <div class="card-body">
                <button type="button" class="lastcardSongs" data-toggle="modal" data-target="#exampleModalCenter">
                    <h2 class="card-title" style="text-align:center">+</h2>
                </button>
            </div>
        </div>
        </div>
    `;
     html += '</div>';
    return html;
}

function addPagination(totalPages) {
    let paginationHTML = `
        <div class="Pagination" style="width:300px; color:red;">
            <ul class="paginationlist" style="width:300px; color:blue; ">
                <li><button class="prev">prev</button></li>
    `;
    for (let i = 0; i < totalPages; i++) {
        paginationHTML += `<li><button class="pagination-number" data-page="${i}">${i + 1}</button></li>`;
    }
    paginationHTML += `
                <li><button class="next">next</button></li>
            </ul>
        </div>
    `;
    return paginationHTML;
}

function renderSongsAndPagination(Songs, totalPages) {
    
    SongsContainer.innerHTML = "";
    

    
    SongsContainer.innerHTML = addLastCardelem(Songs);
    
    
     const lastCard = document.getElementById('last-card');
    if (lastCard) {
        lastCard.insertAdjacentHTML('afterend', `<div class="pagination-container position:relative;">${addPagination(totalPages)}</div>`);
    } else {
        SongsContainer.innerHTML += `<div class="pagination-container">${addPagination(totalPages)}</div>`;
    }
    
 

    attachPaginationEventListeners();
    runOnlyCurrentSong(Songs.songs.length);
}

function attachPaginationEventListeners() {
    const paginationNumbers = document.querySelectorAll('.pagination-number');
    let next = document.querySelector('.next');
    let prev = document.querySelector('.prev');

    next.addEventListener("click", () => {
        page++;
        fetchDataAndUpdateUI(page, pageElementsSize);
    });

    prev.addEventListener("click", () => {
        page--;
        fetchDataAndUpdateUI(page, pageElementsSize);
    });
    
    paginationNumbers.forEach(button => {
        button.addEventListener("click", () => {
            page = parseInt(button.getAttribute('data-page'));
            fetchDataAndUpdateUI(page, pageElementsSize);
        });
    });
}

function runOnlyCurrentSong(songslength) {
    var SongElements = SongsContainer.getElementsByClassName('audio');
    for (let i = 0; i < songslength; i++) {
        let Song = SongElements[i];
        Song.addEventListener('play', () => {
            for (let j = 0; j < SongElements.length; j++) {
                if (SongElements[j] !== Song && !SongElements[j].paused) {
                    SongElements[j].pause();
                }
            }
        });
    }
}

input.addEventListener('input', () => {
    error.style.display = "none";
});

submit_btn.addEventListener("click", SearchButton);

function SearchButton() {
    if (input.value == "" || input.value.length == 0) {
        fetchDataAndUpdateUI(page, pageElementsSize);
        return;
    } else if (input.value !== "" || input.value.length > 0) {
        filterSongs(input.value);
    }
}

/*document.addEventListener("DOMContentLoaded", () => {
    fetchDataAndUpdateUI(page, pageElementsSize);
});*/

async function fetchDataAndUpdateUI(page, pageElementsSize) {
    if (page < 0 || page >= totalPages) {
        return;
    }

    const params = {
        page: page,
        size: pageElementsSize
    };

    const queryString = Object.keys(params)
        .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(params[key]))
        .join('&');

    const url = 'http://localhost:8082/songs?' + queryString;
      //const url = 'http://34.123.66.41:8082/songs?' + queryString;
    //const url=fetchUrl+'/songs'+'?'+queryString;
    try {
        const response = await fetch(url, {
            method: 'GET',
               headers: {
        'Cache-Control': 'no-cache'
    }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const Songs = await response.json();
        renderSongsAndPagination(Songs, totalPages);

        if (Songs.songs.length === 0 && page > 0) {
            document.querySelector('.next').disabled = true;
            return;
        } else if (Songs.songs.length === 0 && page === 0) {
            document.querySelector('.prev').disabled = true;
            return;
        } else {
            document.querySelector('.next').disabled = false;
            document.querySelector('.prev').disabled = false;
        }
    } catch (error) {
        console.error('Error occurred during fetch:', error);
    }
}

function filterSongs(value) {
    const filteredSongs = Songs.songs.filter(song => song.artist.toLowerCase().startsWith(value.toLowerCase()));
    const Songssize = filteredSongs.length;


      console.log("value"+value);
    if (Songssize == 0) {
        submit_btn.setAttribute('data-toggle', 'modal');
        submit_btn.setAttribute('data-target', '#exampleModal');
        modal_body.innerHTML = `No Songs Found with ${input.value}`;
    } else {
        submit_btn.removeAttribute('data-toggle', 'modal');
        modal_body.innerHTML = "";
        let ztml = "";
        filteredSongs.forEach(e => {
            ztml += `
                <div class="card">
                    <div class="card-body">
                        <p>${e.song}</p>
                        <p>${e.artist}</p>
                        <img class="Songimage" src="data:image/png;base64,${e.image}" alt="Song Image">
                        <audio controls class="audio" preload="metadata">
                            <source src="data:audio/mpeg;base64,${e.songFile}" type="audio/mpeg">
                            Your browser does not support the audio element.
                        </audio>
                    </div>
                </div>
            `;
        });
        SongsContainer.innerHTML = ztml;
        runOnlyCurrentSong(filteredSongs.length);
        attachPaginationEventListeners();
    }
}

document.getElementById('songForm').addEventListener('submit', function (event) {
    event.preventDefault();
    const formData = new FormData(this);
    
    //createsongs+'/createsongs'
    fetch(createsongs, {
        method: 'POST',
        body: formData
    })
    .then(response => { 
		
		
		
        if (!response.ok) {
			
			 songmodal.innerHTML = `<p style="color: red;">some error occurred while posting</p>`;

            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        let newcard = `
            <div class="card">
                <div class="card-body">
                    <p>${data.song}</p>
                    <p>${data.artist}</p>
                    <img class="Songimage" src="data:image/png;base64,${data.image}" alt="Song Image">
                    <audio controls class="audio" preload="metadata">
                        <source src="data:audio/mpeg;base64,${data.songFile}" type="audio/mpeg">
                        Your browser does not support the audio element.
                    </audio>
                </div>
            </div>
        `;
        SongsContainer.innerHTML += newcard;
        
        fetchDataAndUpdateUI(page, pageElementsSize)
        attachPaginationEventListeners();
        runOnlyCurrentSong(Songs.songs.length + 1);
        $('#exampleModalCenter').modal('hide');
       
    })
    .catch(error => {
        console.error('There was a problem with form submission:', error);
    });
});

function deleteSong(index){
console.log(index);
   
let 	Id =Songs.songs[index].id;
 let card = document.querySelector(`.card[data-index="${index}"]`);
    console.log(card);
    if (card) {
        card.remove(); 
        let url=`http://localhost:8082/songs/${Id}`;
        
       // let url=`http://34.123.66.41:8082/songs/${Id}`;
        
        //let url=fetchUrl+'/songs'+'/'+`${Id}`;
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.status === 204) {
                card.remove(); // Remove the card from the UI
                console.log('Song deleted successfully');
                alert('Song deleted successfully'); // Optionally show a message to the user
            } else if (response.status === 404) {
                throw new Error('Song not found');
            } else {
                throw new Error('Error deleting song');
            }
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            alert(`Error: ${error.message}`); // Optionally show an error message to the user
        });
    }
        
        
        
    }
    

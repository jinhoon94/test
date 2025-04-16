function selectPlace(placeId) {
fetch(`http://127.0.0.1:8080/api/test?place_id=${placeId}`)
.then(response => response.json())  
.then(data => {
    sessionStorage.setItem('myData', JSON.stringify(data));
    window.location.href = './page1-1.html';
})
.catch(error => console.error('Error fetching data:', error));
}


const logSection = document.querySelector('.history_logs');

const buyButton = document.querySelector('.left_section .buy_button')

const postInput = document.querySelector("#post_input");

buyButton.addEventListener('click', function(){
    const input = postInput.value.trim();
    
    if(input === "" || input > 500)return;

    const log = document.createElement('article')
    log.classList.add('history_log');

    log.innerHTML = `
        <div class="log_left_part">
            <h3>${input} Stocks</h3>
            <!-- <br> -->
            <p>Friday, 02 Feb 1996 21:04</p>
        </div>
        <div class="log_right_part">
            <button class="buy_button button">Buy</button>
        </div>
    `
   
    logSection.prepend(log);
    postInput.value = "";

})

const sellButton = document.querySelector('.left_section .sell_button')

sellButton.addEventListener('click', function(){
    const input = postInput.value.trim();
    if(input === "" || input>500)return;

    const log = document.createElement('article')
    log.classList.add('history_log');

    log.innerHTML = `
        <div class="log_left_part">
            <h3>${input} Stocks</h3>
            <!-- <br> -->
            <p>Friday, 02 Feb 1996 21:04</p>
        </div>
        <div class="log_right_part">
            <button class="sell_button button">Sell</button>
        </div>
    `

    logSection.prepend(log);
    postInput.value = "";

})

const barGraph = document.querySelector('.left_section_graph .bar_graph')
const priceDisplay = document.querySelector(".left_section .left_section_metrics .price_display")
const prevHeight = 320;
let currLength = 20;
setInterval(()=>{
    const max = 500;
    const height = Math.floor(Math.random() * (max - 0 + 1));
    const changeInPrice = height - prevHeight;
    const percentageChange = ((changeInPrice / prevHeight) * 100).toFixed(2);
    let arrow = "&#x2191";
    let chosenColor = "#b2f2bb";
    if(changeInPrice < 0) {
        arrow = "&#x2193;"
        chosenColor = "#ffc9c9";
    }
    priceDisplay.innerHTML = `
    <h2>Price</h2>
    <p>${changeInPrice} <span>${arrow}</span></p>
    <p>${percentageChange}%</p>`
    
    const bar = document.createElement('div')
    bar.classList.add('bar');
    
    bar.innerHTML = `  
    `
    bar.style.height = (height+"px");
    bar.style.backgroundColor = chosenColor
    
    if(chosenColor === "#ffc9c9"){
        bar.style.borderColor = "#e03131";
    }

    currLength+=20;
    if(currLength>900){
        barGraph.removeChild(barGraph.lastElementChild);
        currLength-=20;
    }
    barGraph.prepend(bar);
   

}, 5000)

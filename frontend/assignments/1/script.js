const postsContainer = document.querySelector('.posts');

postsContainer.addEventListener('click', function (event) {

    const likeButton = event.target.closest('.like-post, .unlike-post');
    if (likeButton) {
        const likesCountSpan = likeButton.querySelector('.likes-count');
        const likeImg = likeButton.querySelector('img');

        let likesCount = Number.parseInt(likesCountSpan.textContent, 10);

        if (likeButton.classList.contains('like-post')) {
            likesCount += 1;
            likeButton.classList.remove('like-post');
            likeButton.classList.add('unlike-post');
            likeImg.src = './assets/icons/like-pink.svg';
        } else {
            likesCount = Math.max(0, likesCount - 1);
            likeButton.classList.remove('unlike-post');
            likeButton.classList.add('like-post');
            likeImg.src = './assets/icons/like.svg';
        }

        likesCountSpan.textContent = likesCount;
        return;
    }

    const retweetButton = event.target.closest('.retweet-post, .retweeted-post');
    if (retweetButton) {
        const retweetCountSpan = retweetButton.querySelector('.retweet-count');
        const retweetImg = retweetButton.querySelector('img');

        let count = Number.parseInt(retweetCountSpan.textContent, 10);

        if (retweetButton.classList.contains('retweet-post')) {
            count += 1;
            retweetButton.classList.remove('retweet-post');
            retweetButton.classList.add('retweeted-post');
            retweetImg.src = './assets/icons/retweet-blue.svg';
        } else {
            count = Math.max(0, count - 1);
            retweetButton.classList.remove('retweeted-post');
            retweetButton.classList.add('retweet-post');
            retweetImg.src = './assets/icons/retweet.svg';
        }

        retweetCountSpan.textContent = count;

    }

    const commentButton = event.target.closest(".comment-post");
    if (commentButton) {
        const post = commentButton.closest(".post");
        const commentSection = post.querySelector(".comments-section");

        commentSection.classList.toggle("hidden");
        return;
    }

    const submitButton = event.target.closest(".comment-submit");
    if (submitButton) {
        const post = submitButton.closest(".post");
        const input = post.querySelector(".comment-input");
        const commentsList = post.querySelector(".comments-list");
        const commentCountSpan = post.querySelector(".comments-count");

        const commentText = input.value.trim();
        if (commentText === "") return;

        const comment = document.createElement("p");
        comment.textContent = commentText;
        commentsList.appendChild(comment);

        let count = Number.parseInt(commentCountSpan.textContent, 10);
        commentCountSpan.textContent = count + 1;

        input.value = "";
    }


   
});

const tweetButton = document.querySelector('.content-section .tweet-button');
const postInput = document.querySelector(".post-input");
console.log(postsContainer);
console.log(tweetButton);
console.log(postInput);
tweetButton.addEventListener('click', function () {
    const content = postInput.value.trim();
    console.log(content);
    if (content === "") return;

    const post = document.createElement('article');
    post.classList.add('post');

    post.innerHTML = `
    <div class="profile-icon">
        <img src="./assets/icons/profile-img.png" alt="Profile" class="profile-icon-large" />
    </div>
    <div>
        <div class="user-details">
            <div>
                <span class="user-name">Nitesh Gupta</span>
                <span class="user-handle">@nit_hck</span>
                <span class="post-time">· now</span>
            </div>
            <div>
                <img src="./assets/icons/dots-grey.svg" alt="More Options" class="more-icon" />
            </div>
        </div>
        <div class="post-message">
            <p>${content}</p>
        </div>
        <div class="post-metrics">
            <button type="button" aria-label="Comment" class="comment-post">
                <img src="./assets/icons/comment.svg" alt="" />
                <span class="comments-count">0</span>
            </button>
            <button type="button" aria-label="Retweet" class="retweet-post">
                <img src="./assets/icons/retweet.svg" alt="" />
                <span class="retweet-count">0</span>
            </button>
            <button type="button" aria-label="Like" class="like-post">
                <img src="./assets/icons/like.svg" alt="" />
                <span class="likes-count">0</span>
            </button>
            <button type="button" aria-label="Stats">
                <img src="./assets/icons/stats.svg" alt="" />
            </button>
            <div class="post-actions-right">
                <button type="button" aria-label="Bookmark">
                    <img src="./assets/icons/bookmark-grey.svg" alt="" />
                </button>
                <button type="button" aria-label="Share">
                    <img src="./assets/icons/share.svg" alt="" />
                </button>
            </div>
        </div>
    </div>
    <div class="comments-section hidden">
        <input type="text" class="comment-input" placeholder="Post your reply" />
        <button class="comment-submit">Reply</button>
        <div class="comments-list"></div>
    </div>
    `

    console.log(post);
    postsContainer.prepend(post);
    postInput.value = "";

});

const floatingTweetButton = document.querySelector(".floating-tweet-button");
const tweetBox = document.querySelector(".content-section");

if (floatingTweetButton) {
  floatingTweetButton.addEventListener("click", function () {
    tweetBox.classList.toggle("hidden");
  });
}

const profileIcon = document.querySelector(".profile-icon-toggle");
const sidebarContainer = document.querySelector(".navigation-section-container");
console.log(profileIcon);
console.log(sidebarContainer);
if (profileIcon && sidebarContainer) {
  profileIcon.addEventListener("click", function () {
    sidebarContainer.classList.toggle("show");
  });
}

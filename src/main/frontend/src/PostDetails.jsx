import React, { Component } from 'react';
import Header from './Header.jsx'

class PostDetails extends Component {

    constructor(props) {
        super(props);
        this.state = { post: [], likes: 0 }
        this.likePost = this.likePost.bind(this);
    }


    componentDidMount() {

        let likeAmount = 0;

        fetch("http://localhost:8080/api/posts/" + this.props.match.params.id, {
            method: "GET"
        }).then(response => response.json())
            .then(data => {
                this.setState({post: data, likes: data.likes ? data.likes.length : 0});
            })
    }

    render() {
        return (
            <div className={"container"}>
                <Header/>
                <h1>{this.state.post.title}</h1>
                {this.state.post.content}
                <hr/>
                <div className={"likes-flex"}>
                    <div className={"like-call-to-action u-pull-left"}>
                        <button onClick={this.likePost}>
                            <i class="fas fa-thumbs-up" />
                        </button>
                        <span className={"like-number"}><span id={"likesAmount"}>{this.state.likes}</span></span>
                    </div>
                    <div className={"u-pull-right posted-by"}>
                        Posted by PLACEHOLDER at {this.state.post.createdTime}
                    </div>
                </div>
            </div>
        );
    }

    likePost() {
        fetch("http://localhost:8080/api/posts/like/" + this.state.post.id, {
            method: "POST"

        }).then((res) => console.log(res));
        this.setState({likes: this.state.likes + 1});
        document.querySelector("button").disabled = true;
        let thumb = document.querySelector(".fa-thumbs-up");
        thumb.classList.add("thumb-animation");
        thumb.disabled = true;
    }

    paragraphify(str) {
        let pStr = "<p>";
        str.concat(pStr, str.replace('\n', '</p><p>'));
    }
}

export default PostDetails;
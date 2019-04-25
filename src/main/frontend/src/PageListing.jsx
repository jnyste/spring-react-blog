import React, { Component } from 'react';

class PageListing extends Component {

    constructor(props) {
        super();
        this.state = {totalPages: 0};
    }

    componentDidMount() {
        let fetchUrl = "";

        if (window.location.href.includes("/tag/")) {
            fetchUrl = "/api/posts/tag/";
            let tag = window.location.href.lastIndexOf("/tag/");
            fetchUrl += window.location.href.substr(tag);
        }
        else {
            fetchUrl = "/api/posts/";
        }
            fetch(fetchUrl, {
                method: "GET"
            }).then(response => response.json())
                .then(data => {
                    this.setState({totalPages: data.totalElements});
            })
    }

    render() {
        return (
            <div className={"u-pull-right"}>
                {this.state.totalPages ?
                    Array.apply(null, Array(Math.ceil(this.state.totalPages / 5))).map(function(_, i) {return i + 1}).map((num) => <a key={num} href={"/page/" + num}><button>{num}</button></a>) : null}
            </div>
        );
    }
}

export default PageListing;

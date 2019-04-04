import React, { Component } from 'react';

class PageListing extends Component {

    constructor(props) {
        super();
        this.state = {totalPages: 0};
    }

    componentDidMount() {
        fetch("http://localhost:8080/api/posts/", {
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
                    Array.apply(null, Array(Math.ceil(this.state.totalPages / 5))).map(function(_, i) {return i + 1}).map((num) => <a key={num} href={"/page/" + num}><button>{num}</button></a>) : 0}
            </div>
        );
    }
}

export default PageListing;

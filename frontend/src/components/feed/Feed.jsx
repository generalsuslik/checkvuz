import axios from 'axios';
import { useState, useEffect } from "react";


const BASE_URL = "http://localhost:8080/api/"

const Feed = () => {

    const [universities, setUniversities] = useState([]);

    useEffect(() => {
        axios.get(`${BASE_URL}universities`)
            .then(res => {
                setUniversities(res.data._embedded.universityList);
            })
            .catch(err => {
                console.log(err);
            })
    }, []);

    return (
        <div>
            {universities.map((university) => (
                <div>
                    <span>{university.title}</span>
                    {university.universityImage ? (
                        <img key={university.universityImage.id} src={university.universityImage.imageUrl} />
                    ) : (
                        <span>no image</span>
                    )}
                </div>
            ))}
        </div>
    );
}

export default Feed;


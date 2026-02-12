import { NavLink, useParams } from "react-router-dom";
import { useGetUserByIdQuery } from "../services/userApi"

export function UserDetails() {
    const { id } = useParams<{ id: string }>();
    const { data, error, isLoading, isFetching, isSuccess } = useGetUserByIdQuery(Number(id), { skip: !id });

    return (
        <div>
            <NavLink to="/">Back</NavLink>
            <h1>User Details</h1>
            {isLoading && isFetching && <p>Fetching from server...</p>}
            {!isFetching && <p>Fetching from Cache...</p>}
            {error && <p>Error fetching user details</p>}
            {data && (
                <div>
                    <p>ID: {data.id}</p>
                    <p>Name: {data.firstName} {data.lastName}</p>
                    <p>Email: {data.email}</p>
                    <p>Phone: {data.phone}</p>
                    <p>Age: {data.age}</p>
                    <img src={data.avatar} alt={`${data.firstName} ${data.lastName}`} />
                </div>
            )}
        </div>
    )
}
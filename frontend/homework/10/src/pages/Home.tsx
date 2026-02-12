import { Form } from "../components/form/Form";
import { UsersGrid } from "../components/usersGrid/UsersGrid";

export function Home() {

    

    // console.log(typeof users)
    return (
        <div className="home-page">
            <Form />
            <UsersGrid />
        </div>
    )
}
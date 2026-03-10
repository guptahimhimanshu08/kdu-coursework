import { useAppSelector } from "../../app/store";
import { setAge, setEmail, setFirstName, setLastName, setPhone } from "../../app/userSlice";
import { useAddUserMutation } from "../../services/userApi";
import { useDispatch } from "react-redux";
import { Button } from "../button";
import styles from "./Form.module.scss";

export const Form = () => {

    const { firstName, lastName, email, phone, age } = useAppSelector((state) => state.user);
    const dispatch = useDispatch();

    const [addUser, { data, isLoading, error }] = useAddUserMutation();
    console.log("data:" + data)
    const handleSubmit = async () => {
        console.log(typeof age);

        await addUser({ firstName, lastName, email, phone, age });

        dispatch(setFirstName(""));
        dispatch(setLastName(""));
        dispatch(setEmail(""));
        dispatch(setPhone(""));
        dispatch(setAge(0));
    }
    return (
        <div className={styles["form-container"]}>
            <h1>Add New User</h1>
            <div className={styles["form-inputs"]}>

                <div className={styles["form-fields"]}>
                    FirstName { }
                    <input type="text" placeholder="FirstName" value={firstName} onChange={(e) => dispatch(setFirstName(e.target.value))} />
                </div>
                <div className={styles["form-fields"]}>
                    LastName{ }
                    <input type="text" placeholder="LastName" value={lastName} onChange={(e) => dispatch(setLastName(e.target.value))} />
                </div>
                <div className={styles["form-fields"]}>
                    Email{ }
                    <input type="email" placeholder="Email" value={email} onChange={(e) => dispatch(setEmail(e.target.value))} />
                </div>
                <div className={styles["form-fields"]}>
                    Phone{ }
                    <input type="text" placeholder="Phone" value={phone} onChange={(e) => dispatch(setPhone(e.target.value))} />
                </div>
                <div className={styles["form-fields"]}>
                    Age{ }
                    <input type="number" placeholder="Age" min={1} max={120} value={age} onChange={(e) => dispatch(setAge(Number(e.target.value)))} />
                </div>
            </div>
            <Button onClick={handleSubmit} />
            {error && <p>Error adding user</p>}
            {isLoading && <p>Adding user...</p>}
            {data && (
                <div>
                    <h2>User Added Successfully</h2>
                    <p>ID: {data.id}</p>
                    <p>Name: {data.firstName} {data.lastName}</p>
                </div>
            )}
        </div>
    )
}
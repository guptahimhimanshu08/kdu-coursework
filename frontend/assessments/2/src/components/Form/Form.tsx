import { useState } from "react";
import { useAppDispatch, useAppSelector } from "../../app/store";
import { registerThunk } from "../../slices/registerSlice";
import type { Event } from "../../types/Event";
import { useNavigate } from "react-router-dom";
import { setRegistrationId } from "../../slices/statusSlice";
import styles from "./Form.module.scss";

interface DropDownOptions {
    value: Event,
    label: string
}
const dropdownOptions: DropDownOptions[] = [
    { value: "Concert", label: "concert" },
    { value: "Conference", label: "conference" },
    { value: "Webinar", label: "webinar" },
    { value: "Workshop", label: "workshop" }
]

export const Form = () => {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [eventName, setEventName] = useState("");
    const [message, setMessage] = useState("");
    const dispatch = useAppDispatch();
    const navigate = useNavigate();


    const handleClick = async (e: React.FormEvent) => {
        e.preventDefault();

        const res = await dispatch(registerThunk({ name, email, eventName, message }));
        dispatch(setRegistrationId(res.payload));
        navigate("/confirmation");
    }
    return (
        <div className={styles.container}>

            <form onSubmit={handleClick} className={styles.form}>
                <div className={styles.formFields}>
                    <label htmlFor="name">Name:</label>
                    <input required type="text" id="name" name="name" value={name} onChange={(e) => setName(e.target.value)} />
                </div>
                <div className={styles.formFields}>
                    <label htmlFor="email">Email:</label>
                    <input required type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                </div>
                <div className={styles.formFields}>
                    <label htmlFor="event">Event:</label>
                    <select id="event" value={eventName} onChange={(e) => setEventName(e.target.value)} required>
                        <option value="" disabled>Select an event</option>
                        {dropdownOptions.map((option) => (
                            <option key={option.value} value={option.value}>
                                {option.label}
                            </option>
                        ))}
                    </select>
                </div>

                <div className={styles.formFields}>
                    <label htmlFor="message">Message:</label>
                    <textarea id="message" name="message" value={message} onChange={(e) => setMessage(e.target.value)} />
                </div>

                <div >
                    <button className={styles.formButton} type="submit">Register for Event</button>
                </div>
            </form>
        </div>
    )
}
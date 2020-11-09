import React, {FunctionComponent, PropsWithChildren} from "react";
import TopMenu from "../menu/TopMenu";
import "./DashboardContainer.css"


const DashboardContainer: FunctionComponent = (props: PropsWithChildren<any>) => {
    return (
        <div className="container dashboard-container">
            <div className="d-flex flex-column h-100">
                <section className="flex-shrink-0">
                    <header className="">
                        <div className="p-2 border-bottom">
                        </div>
                        <TopMenu/>
                    </header>
                </section>

                <section className="mt-auto h-100 w-100 d-flex">
                    <main className="p-3 w-100 bg-white">
                        <div className="row mb-5">
                            <div className="col">
                                {props.children}
                            </div>
                        </div>
                    </main>
                </section>
            </div>
        </div>
    )
}

export default DashboardContainer;
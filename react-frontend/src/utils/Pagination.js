import React, { useCallback } from "react";
import { useEffect, useState } from 'react';

const Pagination = (props) => {
	const [pages, setPages] = useState([]);

	const generatePages = useCallback(() => {
		var list = [];
		for (let i = 1; i <= Math.ceil(props?.totalCount / 10); i++) {
			list.push(i);
		}
		setPages(list);
	}, [props?.totalCount])

	useEffect(() => {
		generatePages();
	}, [generatePages])

	const handlePageChange = (doChange, value) => {
		if (!doChange) return;
		if (props?.page + value < 1) return;
		if (props?.page + value > Math.ceil(props?.totalCount / 10)) return;
		props?.setPage(props?.page + value);
	}

  return (
    <div class="d-flex row table-page">
      <div class="page-nums">
        <div className={props?.page <= 1 ? "disabled" : ""} onClick={() => handlePageChange(props?.page <= 1 ? false : true, -1)}>{`< Previous`}</div>
        {pages.map((x) => (
          <div key={x} class={x === props?.page ? "active" : ""} onClick={() => props?.setPage(x)}>{x}</div>
        ))}
        <div className={props?.page >= Math.ceil(props?.totalCount / 10) ? "disabled" : ""} onClick={() => handlePageChange(props?.page >= Math.ceil(props?.totalCount / 10) ? false : true, 1)}>{`Next >`}</div>
      </div>
      <div>Showing items {props?.totalCount > 0 ? (props?.page - 1) * 10 + 1 : 0} ~ {Math.min(props?.page * 10, props?.totalCount)} / {props?.totalCount}</div>
    </div>
  )
}

export default Pagination;
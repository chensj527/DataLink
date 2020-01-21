package com.ucar.datalink.worker.core.runtime.rest.resources;

import com.google.common.eventbus.EventBus;
import com.ucar.datalink.biz.service.MediaService;
import com.ucar.datalink.biz.service.MediaSourceService;
import com.ucar.datalink.biz.utils.DataLinkFactory;
import com.ucar.datalink.biz.utils.DataSourceFactory;
import com.ucar.datalink.common.event.EventBusFactory;
import com.ucar.datalink.common.utils.FutureCallback;
import com.ucar.datalink.domain.event.EsConfigClearEvent;
import com.ucar.datalink.domain.event.HBaseConfigClearEvent;
import com.ucar.datalink.domain.event.KuduConfigClearEvent;
import com.ucar.datalink.domain.media.MediaSourceInfo;
import com.ucar.datalink.domain.media.MediaSourceType;
import com.ucar.datalink.domain.media.parameter.sddl.SddlMediaSrcParameter;
import com.ucar.datalink.worker.api.intercept.InterceptorFactory;
import com.ucar.datalink.worker.api.util.dialect.DbDialectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by sqq on 2017/6/8.
 */
@Path("/flush")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlushResource {
    private static final Logger logger = LoggerFactory.getLogger(FlushResource.class);

    @POST
    @Path("/reloadRdbMediaSource/{mediaSourceId}")
    public void reloadRdbMediaSource(@PathParam("mediaSourceId") String mediaSourceId) throws Throwable {
        logger.info("Receive a request for reload rdb-media-source,with id " + mediaSourceId);
        MediaSourceInfo mediaSourceInfo = DataLinkFactory.getObject(MediaSourceService.class).getById(Long.valueOf(mediaSourceId));
        MediaSourceType mediaSourceType = mediaSourceInfo.getParameterObj().getMediaSourceType();
        if (mediaSourceType.isRdbms()) {
            DataSourceFactory.invalidate(mediaSourceInfo, () -> msPreCloseAction(mediaSourceInfo));
        } else if (mediaSourceType == MediaSourceType.SDDL) {
            List<Long> primaryDbsId = ((SddlMediaSrcParameter) mediaSourceInfo.getParameterObj()).getPrimaryDbsId();
            List<Long> secondaryDbsId = ((SddlMediaSrcParameter) mediaSourceInfo.getParameterObj()).getSecondaryDbsId();
            for (Long primaryDbId : primaryDbsId) {
                MediaSourceInfo ms = DataLinkFactory.getObject(MediaSourceService.class).getById(primaryDbId);
                DataSourceFactory.invalidate(ms, () -> msPreCloseAction(ms));
            }
            for (Long secondaryDbId : secondaryDbsId) {
                MediaSourceInfo ms = DataLinkFactory.getObject(MediaSourceService.class).getById(secondaryDbId);
                DataSourceFactory.invalidate(ms, () -> msPreCloseAction(ms));
            }
        } else if (mediaSourceType == MediaSourceType.VIRTUAL && mediaSourceInfo.getSimulateMsType().isRdbms()) {
            DataSourceFactory.invalidate(mediaSourceInfo, () -> msPreCloseAction(mediaSourceInfo));
        }
        //清空相关Task的mapping缓存
        clearTaskMediaMappingCache(Long.valueOf(mediaSourceId));

    }

    @POST
    @Path("/reloadInterceptor/{interceptorId}")
    public void reloadInterceptor(@PathParam("interceptorId") String interceptorId) throws Throwable {
        logger.info("Receive a request for reload interceptor,with id " + interceptorId);

        InterceptorFactory.invalidateOne(Long.valueOf(interceptorId));
    }

    @POST
    @Path("/reloadEs/{mediaSourceId}")
    public void reloadEs(@PathParam("mediaSourceId") String mediaSourceId) throws Throwable {
        logger.info("Receive a request for reload es-media-source,with id " + mediaSourceId);

        MediaSourceInfo mediaSourceInfo = DataLinkFactory.getObject(MediaSourceService.class).getById(Long.valueOf(mediaSourceId));
        EventBus eventBus = EventBusFactory.getEventBus();
        EsConfigClearEvent event = new EsConfigClearEvent(new FutureCallback(), mediaSourceInfo);
        eventBus.post(event);
        event.getCallback().get();
        //清空相关Task的mapping缓存
        clearTaskMediaMappingCache(Long.valueOf(mediaSourceId));
    }

    @POST
    @Path("/reloadHBase/{mediaSourceId}")
    public void reloadHBase(@PathParam("mediaSourceId") String mediaSourceId) throws Throwable {
        logger.info("Receive a request for reload hbase-media-source,with id " + mediaSourceId);

        MediaSourceInfo mediaSourceInfo = DataLinkFactory.getObject(MediaSourceService.class).getById(Long.valueOf(mediaSourceId));
        EventBus eventBus = EventBusFactory.getEventBus();
        HBaseConfigClearEvent event = new HBaseConfigClearEvent(new FutureCallback(), mediaSourceInfo);
        eventBus.post(event);
        event.getCallback().get();
        //清空相关Task的mapping缓存
        clearTaskMediaMappingCache(Long.valueOf(mediaSourceId));
    }

    @POST
    @Path("/reloadKudu/{mediaSourceId}")
    public void reloadKudu(@PathParam("mediaSourceId") String mediaSourceId) throws Throwable {
        logger.info("Receive a request for reload kudu-media-source,with id " + mediaSourceId);

        MediaSourceInfo mediaSourceInfo = DataLinkFactory.getObject(MediaSourceService.class).getById(Long.valueOf(mediaSourceId));
        EventBus eventBus = EventBusFactory.getEventBus();
        KuduConfigClearEvent event = new KuduConfigClearEvent(new FutureCallback(), mediaSourceInfo);
        eventBus.post(event);
        event.getCallback().get();
        //清空相关Task的mapping缓存
        clearTaskMediaMappingCache(Long.valueOf(mediaSourceId));
    }

    @POST
    @Path("/reloadHDFS/{mediaSourceId}")
    public void reloadHDFS(@PathParam("mediaSourceId") String mediaSourceId) throws Throwable {
        logger.info("Receive a request for reload hdfs-media-source,with id " + mediaSourceId);
        //清空相关Task的mapping缓存
        clearTaskMediaMappingCache(Long.valueOf(mediaSourceId));
    }

    @POST
    @Path("/reloadZK/{mediaSourceId}")
    public void reloadZK(@PathParam("mediaSourceId") String mediaSourceId) throws Throwable {
        logger.info("Receive a request for reload zk-media-source,with id " + mediaSourceId);
        //清空相关Task的mapping缓存
        clearTaskMediaMappingCache(Long.valueOf(mediaSourceId));
    }

    @POST
    @Path("/reloadFQ/{mediaSourceId}")
    public void reloadFQ(@PathParam("mediaSourceId") String mediaSourceId) throws Throwable {
        logger.info("Receive a request for reload fq-media-source,with id " + mediaSourceId);
        //清空相关Task的mapping缓存
        clearTaskMediaMappingCache(Long.valueOf(mediaSourceId));
    }

    @POST
    @Path("/reloadDove/{mediaSourceId}")
    public void reloadDove(@PathParam("mediaSourceId") String mediaSourceId) throws Throwable {
        logger.info("Receive a request for reload dove-media-source,with id " + mediaSourceId);
        //清空相关Task的mapping缓存
        clearTaskMediaMappingCache(Long.valueOf(mediaSourceId));
    }

    public Boolean msPreCloseAction(MediaSourceInfo mediaSourceInfo) {
        DbDialectFactory.invalidate(mediaSourceInfo);
        return true;
    }

    public void clearTaskMediaMappingCache(Long mediaSourceId) {
        List<Long> taskIds = DataLinkFactory.getObject(MediaService.class).findTaskIdsByMediaSourceId(mediaSourceId);
        for (Long taskId : taskIds) {
            DataLinkFactory.getObject(MediaService.class).clearMediaMappingCache(taskId);
        }
    }

}

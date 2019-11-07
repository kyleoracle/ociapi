package com.oracle.sehub.appdev.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.oracle.bmc.core.Compute;
import com.oracle.bmc.core.VirtualNetwork;
import com.oracle.bmc.core.model.Instance;
import com.oracle.bmc.core.model.InstanceSourceViaImageDetails;
import com.oracle.bmc.core.model.LaunchInstanceDetails;
import com.oracle.bmc.core.model.VnicAttachment;
import com.oracle.bmc.core.requests.GetVnicRequest;
import com.oracle.bmc.core.requests.LaunchInstanceRequest;
import com.oracle.bmc.core.requests.ListImagesRequest;
import com.oracle.bmc.core.requests.ListInstancesRequest;
import com.oracle.bmc.core.requests.ListShapesRequest;
import com.oracle.bmc.core.requests.ListVnicAttachmentsRequest;
import com.oracle.bmc.core.requests.TerminateInstanceRequest;
import com.oracle.bmc.core.responses.GetVnicResponse;
import com.oracle.bmc.core.responses.ListImagesResponse;
import com.oracle.bmc.core.responses.ListInstancesResponse;
import com.oracle.bmc.core.responses.ListShapesResponse;
import com.oracle.bmc.core.responses.ListVnicAttachmentsResponse;
import com.oracle.sehub.appdev.autoconfigure.SDKProperties;

@Service
@EnableConfigurationProperties(SDKProperties.class)
public class ComputeService {
	private Compute _compute;
	private VirtualNetwork _virtualNetwork;
	private SDKProperties _sdkProperties;

	@Autowired
	public ComputeService(Compute compute, SDKProperties sdkProperties, VirtualNetwork virtualNetwork) {
		this._compute = compute;
		this._virtualNetwork = virtualNetwork;
		this._sdkProperties = sdkProperties;
	}

	public void launchInstance(String prefix, Integer count) {
		for (int i = 0; i < count; i++) {
			Map<String, String> metadata = new HashMap<>();
			metadata.put("ssh_authorized_keys", _sdkProperties.getSshPublicKey());
			LaunchInstanceDetails details = LaunchInstanceDetails.builder().metadata(metadata).displayName(prefix + i).availabilityDomain(_sdkProperties.getAvailableDomain()).compartmentId(_sdkProperties.getCompartment()).shape(_sdkProperties.getShape()).subnetId(_sdkProperties.getSubnetId()).sourceDetails(InstanceSourceViaImageDetails.builder().imageId(_sdkProperties.getImage()).build()).build();
			LaunchInstanceRequest launchReq = LaunchInstanceRequest.builder().launchInstanceDetails(details).build();

			_compute.launchInstance(launchReq);
		}

	}

	public ListShapesResponse listShapes() {
		ListShapesRequest req = ListShapesRequest.builder().compartmentId(_sdkProperties.getCompartment()).build();
		return _compute.listShapes(req);
	}

	public ListImagesResponse listImages() {
		ListImagesRequest req = ListImagesRequest.builder().compartmentId(_sdkProperties.getCompartment()).build();
		return _compute.listImages(req);
	}

	public List<String> getInstancePublicIp(String instanceId) {
		List<String> ipList = new ArrayList<>();
		// for the instance, list its vnic attachments
		ListVnicAttachmentsResponse listVnicResponse = _compute.listVnicAttachments(ListVnicAttachmentsRequest.builder().compartmentId(_sdkProperties.getCompartment()).instanceId(instanceId).build());
		List<VnicAttachment> vnics = listVnicResponse.getItems();
		for (int i = 0; i < vnics.size(); i++) {
			String vnicId = vnics.get(i).getVnicId();
			GetVnicResponse getVnicResponse = _virtualNetwork.getVnic(GetVnicRequest.builder().vnicId(vnicId).build());
			// then check the vnic for a public IP
			String publicIp = getVnicResponse.getVnic().getPublicIp();
			if (publicIp != null) {
				ipList.add(publicIp);
			}
		}
		return ipList;
	}

	public ListInstancesResponse listInstance(String status) {
		ListInstancesRequest req = ListInstancesRequest.builder().compartmentId(_sdkProperties.getCompartment()).lifecycleState(Instance.LifecycleState.valueOf(status)).build();
		return _compute.listInstances(req);
	}

	public void delete(String prefix) {
		List<Instance> list = this.listInstance(Instance.LifecycleState.Running.toString()).getItems();
		for (Instance i : list) {
			if (i.getDisplayName().startsWith(prefix)) {
				TerminateInstanceRequest req = TerminateInstanceRequest.builder().instanceId(i.getId()).build();
				_compute.terminateInstance(req);
			}
		}
	}

}
